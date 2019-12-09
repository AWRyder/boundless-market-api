package net.wyvernia.boundlessmarketapi.recipes;

import net.wyvernia.boundlessmarketapi.items.Item;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe {

    @Id
    private Integer id;

    @ManyToOne
    private Item outputItem;

    @ElementCollection(targetClass=Integer.class)
    private List<Integer> outputQuantity;

    @OneToMany
    private List<RecipeInput> recipeInputs;

    private String machine;

    private Boolean canHandcraft;

    private String machineLevel;

    @ElementCollection(targetClass=Integer.class)
    private List<Integer> duration;

    private Integer powerRequired;

    @ElementCollection(targetClass=Integer.class)
    private List<Integer> spark;

    private Integer heat;

    private String recipeGroupName;

    @ElementCollection(targetClass=Integer.class)
    private List<Integer> wear;

    private Integer knowledgeUnlockLevel;

    @ManyToOne
    private Item tintTakenFrom;

    private Integer craftXP;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getOutputItem() {
        return outputItem;
    }

    public void setOutputItem(Item outputItem) {
        this.outputItem = outputItem;
    }

    public List<Integer> getOutputQuantity() {
        return outputQuantity;
    }

    public void setOutputQuantity(List<Integer> outputQuantity) {
        this.outputQuantity = outputQuantity;
    }

    public List<RecipeInput> getRecipeInputs() {
        return recipeInputs;
    }

    public void setRecipeInputs(List<RecipeInput> recipeInputs) {
        this.recipeInputs = recipeInputs;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public Boolean getCanHandcraft() {
        return canHandcraft;
    }

    public void setCanHandcraft(Boolean canHandcraft) {
        this.canHandcraft = canHandcraft;
    }

    public String getMachineLevel() {
        return machineLevel;
    }

    public void setMachineLevel(String machineLevel) {
        this.machineLevel = machineLevel;
    }

    public List<Integer> getDuration() {
        return duration;
    }

    public void setDuration(List<Integer> duration) {
        this.duration = duration;
    }

    public Integer getPowerRequired() {
        return powerRequired;
    }

    public void setPowerRequired(Integer powerRequired) {
        this.powerRequired = powerRequired;
    }

    public List<Integer> getSpark() {
        return spark;
    }

    public void setSpark(List<Integer> spark) {
        this.spark = spark;
    }

    public Integer getHeat() {
        return heat;
    }

    public void setHeat(Integer heat) {
        this.heat = heat;
    }

    public String getRecipeGroupName() {
        return recipeGroupName;
    }

    public void setRecipeGroupName(String recipeGroupName) {
        this.recipeGroupName = recipeGroupName;
    }

    public List<Integer> getWear() {
        return wear;
    }

    public void setWear(List<Integer> wear) {
        this.wear = wear;
    }

    public Integer getKnowledgeUnlockLevel() {
        return knowledgeUnlockLevel;
    }

    public void setKnowledgeUnlockLevel(Integer knowledgeUnlockLevel) {
        this.knowledgeUnlockLevel = knowledgeUnlockLevel;
    }

    public Item getTintTakenFrom() {
        return tintTakenFrom;
    }

    public void setTintTakenFrom(Item tintTakenFrom) {
        this.tintTakenFrom = tintTakenFrom;
    }

    public Integer getCraftXP() {
        return craftXP;
    }

    public void setCraftXP(Integer craftXP) {
        this.craftXP = craftXP;
    }
}
